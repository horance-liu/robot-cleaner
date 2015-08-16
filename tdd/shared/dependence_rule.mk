$(TARGET_HOME)%.o : $(SOURCE_HOME)%.cpp
	@$(dep-cmd)
	@$(obj-cmd)
	
$(TARGET_HOME)%.o : $(SOURCE_HOME)%.cc	
	@$(dep-cmd)
	@$(obj-cmd)
	
$(TARGET_HOME)%.o : $(SOURCE_HOME)%.c
	@$(dep-cmd)
	@$(obj-cmd)	
	
$(TARGET_HOME)%.i : $(SOURCE_HOME)%.cpp
	@$(info-cmd)
	
$(TARGET_HOME)%.i : $(SOURCE_HOME)%.cc
	@$(info-cmd)

sinclude $(DEPS2)


