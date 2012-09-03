del Fault.exe
del Fault-obfus.jar
del .Alpha-Fault
del FaultKeyManager.jar
del FaultKeyManager.exe
xcopy C:\Users\Xeology\Documents\NetBeansProjects\FaultKeyManager\dist\FaultKeyManager.jar C:\Users\Xeology\Documents\NetBeansProjects\Fault\game /Y
xcopy C:\Users\Xeology\Documents\NetBeansProjects\Fault\dist\Fault.jar C:\Users\Xeology\Documents\NetBeansProjects\Fault\game /Y
java -jar proguard.jar -include obfus.conf
7za a Fault-obfus.jar com
7za a Fault-obfus.jar org
7za a Fault-obfus.jar jexxus
7za a FaultKeyManager.jar com
7za a FaultKeyManager.jar org
7za a FaultKeyManager.jar jexxus
"C:\Windows.old\Program Files (x86)\Launch4j\launch4j.exe" "C:\Users\Xeology\Documents\NetBeansProjects\Fault\game\faultkey.xml"
"C:\Windows.old\Program Files (x86)\Launch4j\launch4j.exe" "C:\Users\Xeology\Documents\NetBeansProjects\Fault\game\fault.xml"
copy Alpha-Fault.exe .Alpha-Fault
xcopy C:\Users\Xeology\Documents\NetBeansProjects\Fault\game\Alpha-Fault.exe C:\Users\Xeology\Dropbox\Public /Y
xcopy C:\Users\Xeology\Documents\NetBeansProjects\Fault\game\faultversion.txt C:\Users\Xeology\Dropbox\Public /Y
xcopy C:\Users\Xeology\Documents\NetBeansProjects\Fault\game\faultname.txt C:\Users\Xeology\Dropbox\Public /Y
xcopy C:\Users\Xeology\Documents\NetBeansProjects\Fault\game\.Alpha-Fault C:\Users\Xeology\Dropbox\Public /Y