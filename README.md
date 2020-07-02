
# React Native Cybersource Device Fingerprint

## Getting started

`$ yarn add https://github.com/mauriciomartinscruz/react-native-fingerprint-cybersource`

### Manual installation

#### iOS

1. Add pod 'RNFingerprintCybersource', :path => '../node_modules/react-native-fingerprint-cybersource/ios' to your Podfile
2. Run pod install from ios folder
3. Run your project (`Cmd+R`)<

#### Android

NOTE: if you use Reactive Native 0.60+ you can autolinking, ignore this step 1.

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.mauriciomartinscruz.FingerprintCybersource.RNFingerprintCybersourcePackage;` to the imports at the top of the file
  - Add `new RNFingerprintCybersourcePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-fingerprint-cybersource'
  	project(':react-native-fingerprint-cybersource').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-fingerprint-cybersource/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
	dependencies {
		...
		// Cybersource FingerPring by mauriciomartinscruz
		implementation project(':react-native-fingerprint-cybersource')
	}
  	```


## Usage
```javascript
import RNFingerprintCybersource from 'react-native-fingerprint-cybersource'

// INITIALIZE THE SDK
RNFingerprintCybersource.configure(ORG_ID).then( () => {
	console.log('THE CYBERSOURCE INIT IS OK')
})
.catch(err => {
	console.log('THE CYBERSOURCE INIT ERROR IS ', err)
})
// getSession accepts custom attributes for session, check the Cybersource SDK documentation
// example: ['url', 'merchantId', 'customerIdUnique']
RNFingerprintCybersource.getSessionID([]).then( (obj) => {
	console.log(`The session ID is ${obj.sessionId}`)
})
.catch(err => {
	console.log('THE CYBERSOURCE ERROR IS ', err)
})

```
  
