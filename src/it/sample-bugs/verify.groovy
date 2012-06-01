File findbugsXml = new File(basedir, "target/findbugsXml.xml")
def records = new XmlParser().parse(findbugsXml)
def bugs = records.BugInstance
assert 4 == bugs.size()

private String method(bug) {
    return bug.Method.find{ it.'@primary' == 'true' }.'@name'
}

def classUsageBug = bugs.find{ it.'@type' == 'GBU_GUAVA_BETA_CLASS_USAGE' }
assert 'classReference' == method(classUsageBug)

def methodUsageBug = bugs.find{ it.'@type' == 'GBU_GUAVA_BETA_METHOD_USAGE' }
assert 'methodReference' == method(methodUsageBug)

def fieldUsageBugs = bugs.findAll{ it.'@type' == 'GBU_GUAVA_BETA_FIELD_USAGE' }
assert 2 == fieldUsageBugs.size()
assert 'libInstanceFieldReference'== method(fieldUsageBugs[0])
assert 'libStaticFieldReference'== method(fieldUsageBugs[1])
