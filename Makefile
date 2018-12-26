
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

all: out formatter compiler vm

out:
	mkdir -p $(OUT)

formatter: out
	$(MAKE_FORMATTER)
	cp $(FORMATTER_DIRECTORY)/formatter $(OUT)/
compiler: out
	$(MAKE_COMPILER)
	cp $(COMPILER_DIRECTORY)/compiler $(OUT)/
vm: out
	$(MAKE_VM)
	cp $(VM_DIRECTORY)/vm.jar $(OUT)/
	cp -r $(VM_DIRECTORY)/stdlib $(OUT)/stdlib/
	cp -r $(VM_DIRECTORY)/test/ $(OUT)/test/
clean-out:
	rm -rf $(OUT)
clean-formatter:
	$(CLEAN_FORMATTER)
clean-compiler:
	$(CLEAN_COMPILER)
clean-vm:
	$(CLEAN_VM)

clean: clean-out clean-formatter clean-compiler clean-vm 