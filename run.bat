@Echo off
rem set classpath="C:\Program Files\DigitalPersona\Bin\Java\dpfpenrollment.jar";"C:\Program Files\DigitalPersona\Bin\Java\dpfpverification.jar";"C:\Program Files\DigitalPersona\Bin\Java\dpotapi.jar";"C:\Program Files\DigitalPersona\Bin\Java\dpotjni.jar"
rem set classpath="C:\Program Files\DigitalPersona\Bin\Java"
rem echo %classpath%
rem mvn clean package
rem java -cp C:\PROGRA~1\DigitalPersona\Bin\Java\dpfpenrollment.jar:C:\PROGRA~1\DigitalPersona\Bin\Java\dpfpverification.jar:C:\PROGRA~1\DigitalPersona\Bin\Java\dpotapi.jar:C:\PROGRA~1\DigitalPersona\Bin\Java\dpotjni.jar:.  -jar target\sismerenda-1.0-SNAPSHOT.jar 
java -jar target\sismerenda-1.0-SNAPSHOT.jar 