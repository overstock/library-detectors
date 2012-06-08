File findbugsXml = new File(basedir, "target/findbugsXml.xml")
def records = new XmlParser().parse(findbugsXml)
def bugs = records.BugInstance
assert 9 == bugs.size() // five class reference, two method refs, two instance refs

private String method(bug) {
    return bug.Method.find{ it.'@primary' == 'true' }.'@name'
}

def classUsageBugs = bugs.findAll{ it.'@type' == 'GBU_GUAVA_BETA_CLASS_USAGE' }
assert (['betaClassReference',
         'betaClassInstanceFieldReference',
         'betaClassStaticFieldReference',
         'betaClassInstanceMethodReference',
         'betaClassStaticMethodReference'] as Set) ==
   ((classUsageBugs.collect{method(it)}) as Set)

def methodUsageBugs = bugs.findAll{ it.'@type' == 'GBU_GUAVA_BETA_METHOD_USAGE' }
assert (['libInstanceBetaMethodReference', 'libStaticBetaMethodReference'] as Set) ==
  ((methodUsageBugs.collect{method(it)}) as Set)

def fieldUsageBugs = bugs.findAll{ it.'@type' == 'GBU_GUAVA_BETA_FIELD_USAGE' }
assert (['libInstanceBetaFieldReference', 'libStaticBetaFieldReference'] as Set) ==
  ((fieldUsageBugs.collect{method(it)}) as Set)
