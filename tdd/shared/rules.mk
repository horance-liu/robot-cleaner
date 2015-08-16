full-path-srcs =$(extra-srcs)
full-path-srcs += $(all-tests-srcs) 

include $(PROJECT_HOME)/shared/pub_cmd.mk
###############################################################################
clean-all: clean
	@rm -rf $(TARGET_HOME)/src/*.o

$(suite-name)Test.so : $(suite-name)Test.cc $(OBJS2)
	@echo "linking $@ ..."
	@$(LINK) -o $@ $(filter %.cc %.o,$^) -O3 $(INCLUDE) $(LIBS)

$(suite-name)Test : $(suite-name)Test.cc $(OBJS2)
	@echo "linking $@ ..."
	@$(LD) -o $@ $(filter %.cc %.o,$^) $(INCLUDE) $(LIBS) -ltestngpp-static-runner-lib -ltestngpp-utils-shared -ldl

include $(PROJECT_HOME)/shared/dependence_rule.mk
	
