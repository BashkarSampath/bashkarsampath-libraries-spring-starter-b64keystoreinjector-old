package com.bashkarsampath.b64keystore;

import java.io.ByteArrayInputStream;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;

import java.io.IOException;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;

import org.springframework.boot.web.server.Ssl;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.extern.slf4j.Slf4j;

/**

 * Class to inject server with base64-encoded-keystore &

 * base64-encoded-truststore

 * 

 * @author Bashkar Sampath

 * @version 1.0.0-RELEASE

 * @since Nov 18, 2022

 */

@Slf4j

@Component

@NoArgsConstructor

@AllArgsConstructor

public class MyTomcatWebServerCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

	@Autowired	private ServerProperties serverProperties;

	@Value("${server.ssl.overide-key-store-with-b64encoded-inline:#{false}}")

	private boolean overriderWithB64KeyStore;

	@Value("${server.ssl.overide-trust-store-with-b64encoded-inline:#{false}}")

	private boolean overriderWithB64TrustStore;

	@Value("${server.ssl.key-store-b64encoded-inline:''}")

	private String keyStoreB64encoded;

	@Value("${server.ssl.trust-store-b64encoded-inline:''}")

	private String trustStoreB64encoded;

	@Override

	public void customize(TomcatServletWebServerFactory factory) {

		log.info(serverProperties.getSsl().toString());

		Ssl ssl = factory.getSsl();

		if (Boolean.FALSE.equals(this.overriderWithB64KeyStore)) {

			log.info(

					"server.ssl.overide-key-store-with-b64encoded-inline is set to false, skipping base64-encoded-keystore injection");

		} else {

			File generateKeyStore = new File("generatedKeyStore.txt");

			try (FileOutputStream fos = new FileOutputStream(generateKeyStore)) {

				fos.write(new ByteArrayInputStream(Base64.getMimeDecoder().decode(this.keyStoreB64encoded))

						.readAllBytes());

				ssl.setKeyStore(generateKeyStore.getAbsolutePath());

			} catch (FileNotFoundException ex) {

				log.error("Exception while converting base64-encoded-keystore to file for injection: Cause: {}",

						ex.getMessage());

				ex.printStackTrace();

			} catch (IOException ex) {

				log.error("Exception while converting base64-encoded-keystore to file for injection: Cause: {}",

						ex.getMessage());

				ex.printStackTrace();

			}

		}

		if (Boolean.FALSE.equals(this.overriderWithB64KeyStore)) {

			log.info(

					"server.ssl.overide-trust-store-with-b64encoded-inline is set to false, skipping base64-encoded-truststore injection");

		} else {

			File generatedTrustStore = new File("generatedTrustStore.txt");

			try (FileOutputStream fos = new FileOutputStream(generatedTrustStore)) {

				fos.write(new ByteArrayInputStream(Base64.getMimeDecoder().decode(this.trustStoreB64encoded))

						.readAllBytes());

				ssl.setTrustStore(generatedTrustStore.getAbsolutePath());

			} catch (FileNotFoundException ex) {

				log.error("Exception while converting base64-encoded-truststore to file for injection: Cause: {}",

						ex.getMessage());

				ex.printStackTrace();

			} catch (IOException ex) {

				log.error("Exception while converting base64-encoded-truststore to file for injection: Cause: {}",

						ex.getMessage());

				ex.printStackTrace();

			}

		}

	}
 } 
