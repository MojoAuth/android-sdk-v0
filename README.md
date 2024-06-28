<p align="center">
  <a href="https://www.mojoauth.com">
    <img alt="MojoAuth" src="https://mojoauth.com/assets/images/logo.svg" width="200" />
  </a>
</p>

<h1 align="center">
  Android SDK
</h1>


## Prerequisites
Android SDK Version >= 16

Build Tools Version = 29.0.0 (changeable in build.gradle)

Android Studio >= 3.4.1

## Integrate MojoAuth
To start Integrating MojoAuth using Android sdk, follow below mentioned steps:

- To download the SDK, please go to the MojoAuth github repository.

- For Gradle based installation, add the following dependency in your app’s build.gradle:

  ```implementation 'com.mojoauth.android:mojoauth-sdk:1.2.1'```

## Initialize SDK
Before using the SDK, you must initialize the SDK with the help of following code:

```js
MojoAuthSDK.Initialize initialize = new MojoAuthSDK.Initialize();
initialize.setApiKey("<Enter_APIKEY>");
```

## Manifest Settings
After creating a new Android project, follow the installation section of this document. Ensure the MojoAuth Android SDK is linked to your new project as a library.

Add the following permissions to the AndroidManifest.xml:

```<uses-permission android:name="android.permission.INTERNET" />```

## How to contribute

We appreciate all kinds of contributions from anyone, be it finding an issue or writing a blog.

Please check the [contributing guide](CONTRIBUTING.md) to become a contributor.

## License

For more information on licensing, please refer to [License](LICENSE)
