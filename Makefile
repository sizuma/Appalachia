
COMPILER_DIRECTORY = ./compiler
VM_DIRECTORY = ./vm

MAKE_COMPILER = (cd $(COMPILER_DIRECTORY); make)
CLEAN_COMPILER = (cd $(COMPILER_DIRECTORY); make clean)
MAKE_VM = (cd $(VM_DIRECTORY); make jar)
CLEAN_VM = (cd $(VM_DIRECTORY); make clean)

OUT = runtime

all: out compiler vm

out:
	mkdir $(OUT)

compiler: out
	$(MAKE_COMPILER)
	cp $(COMPILER_DIRECTORY)/compiler $(OUT)/
vm: out
	$(MAKE_VM)
	cp $(VM_DIRECTORY)/vm.jar $(OUT)/
	cp -r $(VM_DIRECTORY)/stdlib $(OUT)/stdlib
	cp -r $(VM_DIRECTORY)/test $(OUT)/test

clean-out:
	rm -rf $(OUT)
clean-compiler:
	$(CLEAN_COMPILER)
clean-vm:
	$(CLEAN_VM)

clean: clean-out clean-compiler clean-vm