
Pod::Spec.new do |s|

  s.name         = "RNFingerprintCybersource"
  s.version      = "0.0.1"
  s.summary      = "This library returns the device fingerprint, required for Cybersource mobile implementations"
  s.homepage     = "https://github.com/mauriciomartinscruz/react-native-fingerprint-cybersource"
  s.license      = "MIT"
  s.author       = { "Maurício Martins" => "mauriciomartinscruz@gmail.com" }
  s.platform     = :ios, "9.0"
  s.source       = { :git => "https://github.com/mauriciomartinscruz/react-native-fingerprint-cybersource.git", :tag => "master" }
  s.source_files  = "**/*.{h,m}"
  s.vendored_frameworks = 'TMXProfiling.framework', 'TMXProfilingConnections.framework'
  s.preserve_paths = "**/*.js"
  s.requires_arc = true

  s.dependency "React"
  #s.dependency "others"

end
