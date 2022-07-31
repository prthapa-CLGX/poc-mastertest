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
           //sh('docker-compose -f $WORKSPACE/docker/docker-compose.yml up --detach')
        }

        stage('CT-master-test') {
           sh ('echo "********* Running master tests on live services *********"')
           //sh('./gradlew -i clean test')
        }

        stage("Clean Up") {
            if(anyPocJobRunning(env.JOB_NAME)) {
                sh('echo stopping all running POC containers and removing it')
                sh('docker ps -q -f name=service | xargs docker stop | xargs docker rm')
            }
            sh("Nothing to do...Done Cleaning..")
        }

}

def anyPocJobRunning(currentJobName) {
     boolean res = false;
      Hudson.instance.getAllItems(org.jenkinsci.plugins.workflow.job.WorkflowJob)*.fullName.each {
          if(it.matches("(.*)poc(.*)")) {
            if(!it.matches(currentJobName)) {
                if(Jenkins.instance.getItemByFullName(it).isBuilding()) {
                  res = true;
                  println "POC Job is running: "+it
                }
            }
          }
      }
      return res;
}