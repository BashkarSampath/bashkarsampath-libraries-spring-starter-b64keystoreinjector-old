## com.bashkarsampath.b64keystore
####application.yml - example

```html
server:
  port: 8443
  ssl:
    enabled: true
    client-auth: need
    overide-key-store-with-b64encoded-inline: true #will not override the default keystore with b64 keystore on 'false'
    overide-trust-store-with-b64encoded-inline: true #will not override the default truststore with b64 truststore on 'false'
    key-store: 'classpath:file.p12' will be overridden by 'trust-store-b64encoded-inline' if 'overide-trust-store-with-b64encoded-inline: true'
    key-store-b64encoded-inline: <base64 encoded keystore in a single line>
    key-store-password: pass
    key-store-type: "PKCS12"
    key-alias: privatekey
    key-password: key-password
    trust-store: '' #eg: 'classpath:file.jks' will be overridden by 'trust-store-b64encoded-inline' if 'overide-trust-store-with-b64encoded-inline: true'
    trust-store-b64encoded-inline: <base64 encoded truststore in a single line>
    trust-store-password: pass
    trust-store-type: JKS
```
