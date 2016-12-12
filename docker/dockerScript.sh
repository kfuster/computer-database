case $1 in
	buildMysql )  docker build -t docker-mysql ~/Documents/DockerMysql/ ;;
	runMysql ) docker run -dit --name mysql --net=mynetwork --net-alias mysql docker-mysql ;;
	startMysql ) docker start mysql ;;
	stopMysql ) docker stop mysql ;;
	rmMysql ) docker rm mysql;;
	tagMysql ) docker tag docker-mysql kfuster/computer-database-mysql:latest;;
	pushMysql ) docker push kfuster/computer-database-mysql:latest;;
	buildMavenJdk8 ) docker build -t maven-jdk8 ~/Documents/DockerMavenJdk/ ;;
	runMavenJdk8 ) docker run -dit -v ~/workspace/ComputerDatabase/computer-database:/usr/share/computer-database --name maven_jdk8 --net=mynetwork --net-alias maven maven-jdk8 ;;
	startMavenJdk8 ) docker start maven_jdk8 ;;
	stopMavenJdk8 ) docker stop maven_jdk8 ;;
	rmMavenJdk8 ) docker rm maven_jdk8 ;;
	tagMavenJdk8 ) docker tag maven-jdk8 kfuster/computer-database-maven-jdk8:latest;;
	pushMavenJdk8 ) docker push kfuster/computer-database-maven-jdk8:latest;;
	createNetwork ) docker network create -d bridge --subnet 172.25.0.0/16 mynetwork ;;
	connectMysql ) docker network connect mynetwork mysql ;;
	connectMavenJdk8 ) docker network connect mynetwork maven_jdk8 ;;
	buildJenkins ) docker build -t docker-jenkins ~/Documents/DockerJenkins;;
	runJenkins ) docker run -dit -v /var/run/docker.sock:/var/run/docker.sock \
	-v $(which docker):/usr/bin/docker -p 8081:8080 \
	-v ~/jenkins:/var/jenkins_home --name jenkins docker-jenkins;;
	startJenkins ) docker start jenkins;;
	stopJenkins ) docker stop jenkins;;
	rmJenkins ) docker rm jenkins;;
	tagJenkins ) docker tag jenkins kfuster/computer-database-jenkins:latest;;
	pushJenkins ) docker push kfuster/computer-database-jenkins:latest;;
esac
