pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'

    }
     environment {
            GRAFANA_API_KEY = credentials('Grafana')
            GRAFANA_URL = 'http://192.168.50.4:3000'
            DASHBOARD_IDS = 'haryan-jenkins,spring,test' // Comma-separated list of dashboard IDs
     }

    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
            }
        }

        stage('Git') {
            steps {
                git branch: 'ben_younes_idriss', url: 'https://github.com/CAPDEV-ORG/DevOps-ProjectFoyer.git/'
            }
        }




        stage('Testing Maven') {
            steps {
                sh 'mvn -version'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Display Date') {
            steps {
                script {
                    def currentDate = new Date()
                    echo "Current Date: ${currentDate}"
                }
            }
        }
      /*  stage('SonarQube Analysis') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'SONAR_TOKEN', variable: 'SONAR_TOKEN')]) {
                        echo "SONAR_TOKEN: $SONAR_TOKEN"
                        sh """
                            mvn clean verify sonar:sonar \
                            -Dsonar.projectKey=CapDev \
                            -Dsonar.projectName=CapDev \
                            -Dsonar.host.url=http://192.168.50.4:9000 \
                            -Dsonar.token=\$SONAR_TOKEN
                        """
                    }
                }
            }
        }
         stage('Deploy nexus') {
            steps {
                script {
                    sh 'mvn deploy -DskipTests'
                }
            }
         }*/
                 stage('Créer l’Image Docker') {
                     steps {
                         script {
                             sh "docker build -t idross/tp-foyer:5.0.0 ."
                         }
                     }
                 }

                stage('Push Docker Image') {
                    steps {
                        script {
                            withCredentials([string(credentialsId: 'DOCKER-TOKEN', variable: 'DOCKER_TOKEN')]) {
                                sh '''
                                    echo "${DOCKER_TOKEN}" | docker login -u idross --password-stdin
                                    docker push idross/tp-foyer:5.0.0
                                    docker logout
                                '''
                            }
                        }
                    }
                }


                 stage('Déployer avec Docker Compose') {
                     steps {
                         script {
                             sh 'sudo docker-compose down --remove-orphans' // Arrêter les conteneurs existants
                             sh 'sudo docker-compose up -d --remove-orphans' // Démarrer les nouveaux conteneurs en arrière-plan
                         }
                     }
                 }


                        stage('Monitoring Grafana') {
                             steps {
                                 script {
                                     def dashboardIds = DASHBOARD_IDS.split(',')

                                     for (dashboardId in dashboardIds) {
                                         // Prepare the curl command without using string interpolation directly with GRAFANA_API_KEY
                                         def command = "curl -s -H \"Authorization: Bearer ${env.GRAFANA_API_KEY}\" ${GRAFANA_URL}/api/dashboards/uid/${dashboardId.trim()}"

                                         // Execute the command securely
                                         def response = sh(script: command, returnStdout: true).trim()
                                         echo "Fetched Grafana Dashboard ${dashboardId}: ${response}"
                                     }
                                 }
                             }
                         }
    }

    post {
        success {
            emailext(
                subject: "SUCCESS: Build #${currentBuild.number} - ${currentBuild.fullDisplayName}",
                body: """
                    <h2>Build Successful!</h2>
                    <p>The build was successful!</p>
                    <p>Check it out here: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                    <p>Commit: <strong>${env.GIT_COMMIT}</strong></p>
                    <p>Branch: <strong>${env.GIT_BRANCH}</strong></p>
                    <p>Duration: <strong>${currentBuild.durationString}</strong></p>
                """,
                to: 'idriss.benyounes@esprit.tn',
                mimeType: 'text/html' // Use HTML for better formatting
            )
        }
        failure {
            emailext(
                subject: "FAILED: Build #${currentBuild.number} - ${currentBuild.fullDisplayName}",
                body: """
                    <h2>Build Failed!</h2>
                    <p>The build has failed.</p>
                    <p>Check the logs here: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                    <p>Commit: <strong>${env.GIT_COMMIT}</strong></p>
                    <p>Branch: <strong>${env.GIT_BRANCH}</strong></p>
                    <p>Triggered by: <strong>${currentBuild.getCause(hudson.model.Cause$PushCause)?.shortDescription ?: 'N/A'}</strong></p>
                """,
                to: 'idriss.benyounes@esprit.tn',
                mimeType: 'text/html'
            )
        }
    }
}
