Function Download-File ($url,$out) {
	Write-Output ""
	Write-Output "Downloading $url to $out,please wait ...";
	$webClient = (New-Object Net.WebClient)
	$webClient.Headers.Add("Cookie","oraclelicense=accept-securebackup-cookie")
	$webClient.DownloadFile($url,$out)
}

Function Check-Cmd ($command){
	return [bool](Get-Command -Name $command -ErrorAction SilentlyContinue)
}

$location = Read-Host -Prompt 'Location to install'

if (-Not (Check-Cmd "java")) {
	Write-Output "installing java 8 in you system"
	$java = "$location\jdk-8u111-windows-x64.exe"
	Download-File "http://download.oracle.com/otn-pub/java/jdk/8u111-b14/jdk-8u111-windows-x64.exe" "$location/$java"
	& jdk-8u111-windows-x64.exe
}

if (-Not (Test-Path "$location/nginx-admin-standalone-1.0.0-swarm.jar")) {
	Download-File "https://bintray.com/jslsolucoes/nginx-admin/download_file?file_path=nginx-admin-standalone-1.0.0-swarm.jar" "$location/nginx-admin-standalone-1.0.0-swarm.jar"
}
Start-Process java -ArgumentList '-jar', '$location/nginx-admin-standalone-1.0.0-swarm.jar','-dbuser sa','-dbpassword sa'



