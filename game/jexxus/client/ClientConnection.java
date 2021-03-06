package jexxus.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

import jexxus.common.ByteUtils;
import jexxus.common.Connection;
import jexxus.common.Delivery;

/**
 * Used to establish a connection to a server.
 * 
 * @author Jason
 * 
 */
public class ClientConnection implements Connection {

	private ClientConnectionListener listener;
	private final Socket tcpSocket;
	private DatagramSocket udpSocket;
	protected final String serverAddress;
	protected final int tcpPort, udpPort;
	private DatagramPacket packet;
	private boolean connected = false;
	private InputStream tcpInput;
	private OutputStream tcpOutput;
	private final byte[] header = new byte[4];

	/**
	 * Creates a new connection to a server. The connection is not ready for use until <code>connect()</code> is called.
	 * 
	 * @param listener
	 *            The responder to special events such as receiving data.
	 * @param serverAddress
	 *            The IP address of the server to connect to.
	 * @param tcpPort
	 *            The port to connect to the server on.
	 */
	public ClientConnection(ClientConnectionListener listener, String serverAddress, int tcpPort) {
		this(listener, serverAddress, tcpPort, -1);
	}

	/**
	 * Creates a new connection to a server. The connection is not ready for use until <code>connect()</code> is called.
	 * 
	 * @param listener
	 *            The responder to special events such as receiving data.
	 * @param serverAddress
	 *            The IP address of the server to connect to.
	 * @param tcpPort
	 *            The port to send data using the TCP protocol.
	 * @param udpPort
	 *            The port to send data using the UDP protocol.
	 */
	public ClientConnection(ClientConnectionListener listener, String serverAddress, int tcpPort, int udpPort) {
		if (listener == null)
			throw new RuntimeException("You must supply a connection listener.");
		this.listener = listener;
		this.serverAddress = serverAddress;
		this.tcpPort = tcpPort;
		this.udpPort = udpPort;
		tcpSocket = new Socket();
		if (udpPort != -1) {
			try {
				packet = new DatagramPacket(new byte[0], 0, new InetSocketAddress(serverAddress, udpPort));
				udpSocket = new DatagramSocket();
			} catch (IOException e) {
				System.err.println("Problem initializing UDP on port " + udpPort);
				System.err.println(e.toString());
			}
		}
	}

	/**
	 * Tries to open a connection to the server.
	 * 
	 * @return true if the connection was successful, false otherwise.
	 */
	public synchronized boolean connect() {
		if (connected) {
			System.err.println("Tried to connect after already connected!");
			return true;
		}
		try {
			tcpSocket.connect(new InetSocketAddress(serverAddress, tcpPort));
			tcpInput = new BufferedInputStream(tcpSocket.getInputStream());
			tcpOutput = new BufferedOutputStream(tcpSocket.getOutputStream());
			startTCPListener();
			connected = true;
			if (udpPort != -1) {
				startUDPListener();
				send(new byte[0], Delivery.UNRELIABLE);
			}
			/*
			 * byte[] portBuf = new byte[4]; ByteUtils.pack(udpPort, portBuf); tcpOutput.write(portBuf); tcpOutput.flush();
			 */
			return true;
		} catch (IOException e) {
			System.err.println("Problem establishing TCP connection to " + serverAddress + ":" + tcpPort);
			System.err.println(e.toString());
			return false;
		}
	}

	@Override
	public synchronized void send(byte[] data, Delivery deliveryType) {
		if (connected == false) {
			System.err.println("Cannot send message when not connected!");
			return;
		}
		ByteUtils.pack(data.length, header);
		if (deliveryType == Delivery.RELIABLE) {
			// send with TCP
			try {
				tcpOutput.write(header);
				tcpOutput.write(data);
				tcpOutput.flush();
			} catch (IOException e) {
				System.err.println("Error writing TCP data.");
				System.err.println(e.toString());
			}
		} else if (deliveryType == Delivery.UNRELIABLE) {
			if (udpPort == -1) {
				System.err.println("Cannot send Unreliable data unless a UDP port is specified.");
				return;
			}
			packet.setData(data);
			try {
				udpSocket.send(packet);
			} catch (IOException e) {
				System.err.println("Error writing UDP data.");
				System.err.println(e.toString());
			}
		}
	}

	private void startTCPListener() {
		Thread t = new Thread(new Runnable() {
			final byte[] headerInput = new byte[4];

			public void run() {
				while (true) {
					try {
						if (tcpInput.read(headerInput) == -1)
							throw new IOException("Ended.");
						int len = ByteUtils.unpack(headerInput);
						byte[] ret = new byte[len];
						int count = 0;
						while (count < len) {
							count += tcpInput.read(ret, count, len - count);
						}
						listener.receive(ret);
					} catch (IOException e) {
						if (connected) {
							connected = false;
							listener.connectionBroken(false);
						} else {
							listener.connectionBroken(true);
						}
						if (udpSocket != null) {
							udpSocket.close();
						}
						break;
					}
				}
			}
		});
		t.start();
	}

	private void startUDPListener() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				final int BUF_SIZE = 2048;
				final DatagramPacket inputPacket = new DatagramPacket(new byte[BUF_SIZE], BUF_SIZE);
				while (true) {
					try {
						udpSocket.receive(inputPacket);
						byte[] ret = Arrays.copyOf(inputPacket.getData(), inputPacket.getLength());
						listener.receive(ret);
					} catch (IOException e) {
						if (connected) {
							connected = false;
						}
						break;
					}
				}
			}
		});
		t.start();
	}

	@Override
	public void close() {
		if (connected) {
			System.err.println("Cannot close the connection when it is not connected.");
		} else {
			try {
				tcpSocket.close();
				tcpInput.close();
				tcpOutput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			connected = false;
		}
	}

	@Override
	public boolean isConnected() {
		return connected;
	}

	/**
	 * Sets the connection listener.
	 */
	public void setConnectionListener(ClientConnectionListener listener) {
		this.listener = listener;
	}
}
