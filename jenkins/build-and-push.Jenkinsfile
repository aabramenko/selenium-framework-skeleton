pipeline {
  environment {
    registry = "alekseiabramenko/selenium-test-environment"
    registryCredential = 'dockerhub'
    dockerImage = ''
  }
  agent any
  stages {
    stage('Cloning Git') {
      steps {
        git 'https://github.com/aabramenko/selenium-framework-skeleton.git'
      }
    }
    stage('Building image') {
      steps{
        script {
          dockerImage = docker.build(registry, "-f docker/Dockerfile .")
        }
      }
    }
    stage('Deploy Image') {
      steps{
        script {
          docker.withRegistry("https://registry.hub.docker.com", registryCredential) {
            dockerImage.push("${BUILD_NUMBER}")
            app.push("latest")
          }
        }
      }
    }
    stage('Remove Unused docker image') {
      steps{
        sh "docker rmi $registry:$BUILD_NUMBER"
        sh "docker rmi $registry:latest"
      }
    }
  }
}