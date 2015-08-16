extra-srcs+= $(shell find $(PROJECT_HOME)/src -name "*.cpp")

include-paths += $(PROJECT_HOME)/include

include $(PROJECT_HOME)/shared/rules.mk
