include-paths += . \
	      /usr/local/3rdparty  \
              $(mockcpp-include-path)  \
              $(testngpp-include-path) \
              $(PROJECT_HOME)/include
              
           
INCLUDE += $(addprefix -I, $(abspath $(include-paths)))

LIBS += -L$(testngpp-lib-path) -ltestngpp -ltestngpp-user -lstdc++

DEFS = -D_PRODUCT_TYPE=10 

CC=gcc -O3 -std=c++1y -Wall -Wno-invalid-offsetof $(DEFS)

#LINK=$(CC) -fPIC -shared -Wl,--no-undefined
LINK=$(CC) -fPIC -shared

LD=$(CC) -Wl,--no-undefined

AR = ar crs

MV = mv
RM = rm -rf
SED = sed
