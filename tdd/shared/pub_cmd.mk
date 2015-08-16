include $(PROJECT_HOME)/shared/make_depend.mk

SRCS=$(abspath $(full-path-srcs))
OBJS=$(patsubst %.cc, %.o, $(SRCS:.cpp=.o)) 
DEPS=$(patsubst %.cc, %.d, $(SRCS:.cpp=.d))
INFS=$(patsubst %.cc, %.i, $(SRCS:.cpp=.i))

TEMP_PATH = /tempfile

SOURCE_HOME = $(PROJECT_HOME)
TARGET_HOME = $(PROJECT_HOME)$(TEMP_PATH)

OBJS2=$(subst $(SOURCE_HOME),$(TARGET_HOME), $(OBJS))
DEPS2=$(subst $(SOURCE_HOME),$(TARGET_HOME), $(DEPS))
INFS2=$(subst $(SOURCE_HOME),$(TARGET_HOME), $(INFS))

dep-cmd = $(call make-depend,$<,$@,$(subst .o,.d,$@))	

info-cmd= echo "generating $@ ..."; mkdir -p $(dir $@); $(CC) -E -o $@ -c $< $(INCLUDE); echo "done"
obj-cmd = echo "generating $@ ..."; rm -f $@; $(CC) -o $@ -c $< -D__SHORT_FILE__="\"$(<F)\"" $(INCLUDE); echo "done"
