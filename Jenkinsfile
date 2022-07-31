node {
        stage('SCM checkout') {
            checkout([$class: 'GitSCM',
               branches: [[name: '*/develop']],
               doGenerateSubmoduleConfigurations: false,
               extensions: [[$class: 'CleanBeforeCheckout']],
               submoduleCfg: [],
               userRemoteConfigs: [[url: 'https://github.com/prthapa-CLGX/poc-mastertest.git']]
            ])
        }

        stage('Deploy all services') {
            sh('docker-compose -f $WORKSPACE/docker/docker-compose.yml up --detach')
        }

        stage('CT-master-test') {
           sh ('echo "********* Running master tests on live services *********"')
           sh('./gradlew -i clean test')
        }

}