del Fault.exe
del Fault-obfus.jar
xcopy C:\Users\Xeology\Documents\NetBeansProjects\Fault\dist\Fault.jar C:\Users\Xeology\Documents\NetBeansProjects\Fault\game /Y
java -jar proguard.jar -include obfus.conf
7za a Fault-obfus.jar com
7za a Fault-obfus.jar org
"C:\Windows.old\Program Files (x86)\Launch4j\launch4j.exe" "C:\Users\Xeology\Documents\NetBeansProjects\Fault\game\fault.xml"
xcopy C:\Users\Xeology\Documents\NetBeansProjects\Fault\game\Fault.exe C:\Users\Xeology\Dropbox\Public /Y
xcopy C:\Users\Xeology\Documents\NetBeansProjects\Fault\game\faultversion.txt C:\Users\Xeology\Dropbox\Public /Y