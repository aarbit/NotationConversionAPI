# Notation Conversion API

## About
A Spring Boot application that provides an API to convert one mathematical notation into another. Supported notations are: postfix, prefix, and infix.

## Running
Given a properly setup Java JDK (v8) installation, on the command line, run:
    
```
    $ ./gradlew -PSECRET_KEY="{your-secret-key}" bootRun
```
where {your-secret-key} is any string of your choice. It is used for JWT signing.

## Usage

### POST: /user/signup
```
    body: {
        "username":"{username-you-like}",
        "password":"{password-you-like}"
    }
```

### POST: /login
```
    body: {
        "username":"{username-you-chose}",
        "password":"{password-you-chose}"
    }
```
Use the content of the "Authorization" response header in subsequent POSTs' Authorization headers

### POST: /convert/output/{notation-type}
```
    notation-type: String (e.g. postfix)
    body: String (e.g. 1 + 2)
    response: String (e.g. 1 2 +)
```
where {notation-type} is the style of notation to be used in the output (options are: `postfix`, `prefix`, `infix`), 
the body is a mathematical expression as a plaintext string, 
and the response is a string of the converted expression in the same format as the body.

## Note
The server's port defaults to 8080
JWT timeout is 5min