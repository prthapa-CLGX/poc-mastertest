import hudson.model.*

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
            checkIfServiceRunning(env.JOB_NAME})
            //sh('docker-compose -f $WORKSPACE/docker/docker-compose.yml up --detach')
        }

        stage('CT-master-test') {
           sh ('echo "********* Running master tests on live services *********"')
          // sh('./gradlew -i clean test')
        }

}

def checkIfServiceRunning(jobName) {
      Hudson.instance.getAllItems(org.jenkinsci.plugins.workflow.job.WorkflowJob)*.fullName.each {
        	 println it
    	     println jobName
          if(!Jenkins.instance.getItemByFullName(it).isBuilding()){
            println 'Not Running'

          }
          if(it.matches("(.*)poc(.*)")) {
            println 'matched job name: '+ it
          }

      }
}