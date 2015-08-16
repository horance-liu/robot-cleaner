# this file implement the makefile function for dependencies rules

# $(call make-depend, source_file, object-file, depend-file)
define make-depend
	mkdir -p $(dir $3);
	$(CC) $(CFLAGS) $(CPPFLAGS) -MM $(INCLUDE) $(TARGET_ARCH) $1 > $3.tmp1;
	$(SED) 's,\($(notdir $*)\)\.o[ :]*,$2 $3 : ,g' < $3.tmp1 > $3.tmp
	$(SED)  -e 's/#.*//'                                           \
	-e 's/^[^:]*: *//'                                             \
	-e 's/ *\\$$//'                                                \
	-e '/^$$/ d'                                                   \
	-e 's/$$/ :/' $3.tmp >> $3.tmp
	$(MV) $3.tmp $3
	$(RM) $3.tmp1
endef

# should use the make-depend as follow
#%.o: %.c
#	$(call make-depend,$<,$@,$(subst .o,.d,$@))
#	$(CC) -o $@ $<
