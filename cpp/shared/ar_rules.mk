full-path-srcs = $(shell find $(PROJECT_HOME)/$(lib-name)/src -name *.cpp)
INCLUDE+=-I$(PROJECT_HOME)/$(lib-name)/include
ARS = $(PROJECT_HOME)/output/lib$(lib-name).a
ar-cmd = echo "generating $@ ..."; $(AR) $@ $^

include $(PROJECT_HOME)/shared/pub_cmd.mk

.PHONY:all clean

all:$(ARS)

$(ARS):$(OBJS2)
	@$(ar-cmd)

clean:
	@rm -rf $(TARGET_HOME)/$(lib-name);\
	rm -f $(ARS)

include $(PROJECT_HOME)/shared/dependence_rule.mk
