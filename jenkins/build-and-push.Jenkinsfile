pipeline {
    node {
        stage('Pull code') {
            git 'https://github.com/aabramenko/selenium-framework-skeleton.git'
        }
        stage('Build Image') {
            steps {
                script {
                	app = docker.build("alekseiabramenko/selenium-test-environment", "-f docker/Dockerfile .")
                }
            }
        }
        stage('Push Image') {
            steps {
                script {
			        docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
			        	app.push("${BUILD_NUMBER}")
			            app.push("latest")
			        }
                }
            }
        }
    }
}