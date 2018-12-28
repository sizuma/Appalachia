all: runtime

out:
	mkdir -p runtime
	mkdir -p runtime/stdlib
	mkdir -p runtime/test
	
vm/vm.jar:
	(cd ./vm ; make)

native/formatter:
	(cd native; make formatter)
native/compiler:
	(cd native; make compiler)
native/preprocessor:
	(cd native; make preprocessor)
runtime: out native/formatter native/compiler native/preprocessor vm/vm.jar
	cp -f native/formatter runtime/
	cp -f native/preprocessor runtime/
	cp -f native/compiler runtime/
	cp -f vm/vm.jar runtime/
	cp -rf vm/stdlib/* runtime/stdlib
	cp -rf vm/test/* runtime/test

clean-out:
	rm -rf runtime/*
	rm -rf runtime/.tmp/
clean-native:
	(cd native; make clean)
clean-vm:
	(cd vm; make clean)

clean: clean-out clean-native clean-vm 