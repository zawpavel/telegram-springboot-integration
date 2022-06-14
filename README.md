# telegram-springboot-integration
Example of [pengrad telegram library](https://github.com/pengrad/java-telegram-bot-api) integration with Spring Boot  

## Development  
Fill processors in `com.integrationexample.updateprocessor` package with your own logic and add your own commands. 
You must choose whether to use long polling (see `UpdateListener`) or webhook (see `WebhookController`) to process your telegram Upates   

## Long polling  
Using long polling is much easier and does not require generation of certificates because it communicates over HTTP. It might be slower though.
Just take a look at `UpdateListener`. 

## Webhook  
Webhook is better but a bit more complicated.  
To use it, you must use HTTPS, then you need a TLS/SSL certificate.  
You can use some service like `Let's Encrypt` or create a self-signed certificate.  

### Generate self-signed certificate  
For example, you ordered a VPS with IP address `1.2.3.4` and want to generate a self-signed certificate for it  
Let's generate your own certificate with java tool `keytool`:  
```
keytool -genkeypair -alias YOUR_ALIAS -keyalg RSA -keysize 4096 -validity 3650 -dname "CN=1.2.3.4" -keypass $KS_PASSWORD -keystore yourp12file.p12 -storeType PKCS12 -storepass $STOREPASS
```
Put your `.p12` file to `resources` folder to use it in Spring Boot application later  
You will need `.pem` file to upload it to the Telegram server (see `setWebhook` method in Telegram API)  
To convert `.p12` -> `.pem` :  
``` openssl pkcs12 -in your_p12_file.p12 -out your_pem_file.pem ```  
After uploading `.pem` file to the Telegram server you can use your `.p12` file in Spring Boot application (see `application.yml`) 

## Deploy  
Build your application with `maven`:  
```mvn clean package```  
Upload `.jar` file to the VPS via `scp`:  
```scp ./target/app.jar user@remote_host:remote_file```  
Login to the VPS via `ssh` and run your application:  
```java -jar app.jar``` 

## Test self-signed certificate
Make sure you have configured the certificate correctly with `curl`:  
```curl --cacert memorapv.pem -v https://1.2.3.4:8443/health```  

## Set up database  
The fastest way is to use docker:  
```docker run --name memora-postgres -e POSTGRES_USER=$DB_USER -e POSTGRES_PASSWORD=$DB_PASSWORD -p 5432:5432 -v /data:/var/lib/postgresql/data -d postgres```
