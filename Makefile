
FORMATTER_DIRECTORY = ./formatter
COMPILER_DIRECTORY = ./compiler
VM_DIRECTORY = ./vm

MAKE_FORMATTER = (cd $(FORMATTER_DIRECTORY); make)
CLEAN_FORMATTER = (cd $(FORMATTER_DIRECTORY); make clean)
MAKE_COMPILER = (cd $(COMPILER_DIRECTORY); make)
CLEAN_COMPILER = (cd $(COMPILER_DIRECTORY); make clean)
MAKE_VM = (cd $(VM_DIRECTORY); make jar)
CLEAN_VM = (cd $(VM_DIRECTORY); make clean)

OUT = runtime
STD_LIB = stdlib
TEST = test

all: runtime

out:
	mkdir -p $(OUT)
	mkdir -p $(OUT)/$(STD_LIB)
	mkdir -p $(OUT)/$(TEST)

formatter: out
	$(MAKE_FORMATTER)
	
compiler: out
	$(MAKE_COMPILER)
	
vm: out
	$(MAKE_VM)
	
runtime: out formatter compiler vm
	cp -f $(FORMATTER_DIRECTORY)/formatter $(OUT)/
	cp -f $(COMPILER_DIRECTORY)/compiler $(OUT)/
	cp -f $(VM_DIRECTORY)/vm.jar $(OUT)/

	cp -rf $(VM_DIRECTORY)/$(STD_LIB)/* $(OUT)/$(STD_LIB)/
	cp -rf $(VM_DIRECTORY)/$(TEST)/* $(OUT)/$(TEST)/
clean-out:
	rm -rf $(OUT)
clean-formatter:
	$(CLEAN_FORMATTER)
clean-compiler:
	$(CLEAN_COMPILER)
clean-vm:
	$(CLEAN_VM)

clean: clean-out clean-formatter clean-compiler clean-vm 