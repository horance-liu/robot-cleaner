include $(PROJECT_HOME)/shared/defs.mk

TEST_GENERATOR=$(PYTHON) $(TESTNGPP_ROOT)/testngpp/generator/testngppgen.pyc 
TEST_SUITE_GENERATOR=$(PYTHON) $(TESTNGPP_ROOT)/testngpp/generator/testng-suite-gen.pyc 

testngpp-include-path=$(TESTNGPP_ROOT)/include
mockcpp-include-path=$(MOCKCPP_ROOT)/include

testngpp-lib-path=$(TESTNGPP_ROOT)/lib
mockcpp-lib-path=$(MOCKCPP_ROOT)/lib

all-tests-files=$(shell ls $(PWD)/Test*.h)
all-tests-srcs=$(subst .h,.cc,$(all-tests-files))
suite-name=$(notdir $(PWD))
project-home=$(PROJECT_HOME)
extra-srcs=
include-paths=

TESTSUITE=$(suite-name)Test

.PHONY: all clean clean-all build-only

all : build-only
	@$(testngpp-runner) ./$(TESTSUITE).so -L$(TESTNGPP_ROOT)/testngpp/listener -l"testngppstdoutlistener -v -c -s " $(test-filter) $(tags) -s  

nocolor-all : build-only
	@$(testngpp-runner) ./$(TESTSUITE).so -L$(TESTNGPP_ROOT)/testngpp/listener -l"testngppstdoutlistener -s " $(test-filter) $(tags) -s

bin : $(TESTSUITE)
	@./$(TESTSUITE) -L$(TESTNGPP_ROOT)/testngpp/listener -l"testngppstdoutlistener -v -c -s " $(test-filter) $(tags) -s

build-only: $(TESTSUITE).so

clean : 
	@rm -f $(TESTSUITE) $(TESTSUITE).so $(PWD)/*.cc

$(suite-name)Test.cc :
	@echo "generating $@ ..."
	@$(TEST_SUITE_GENERATOR) $*

Test%.cc:Test%.h
	@$(TEST_GENERATOR) -o $@ $^

include $(PROJECT_HOME)/shared/cfg.mk

