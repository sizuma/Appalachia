
all: cli.jar gui.jar

out/cli/CoomandLineInterface.class: src/cli/CommandLineInterface.java
	javac -d out/ -sourcepath src/ src/cli/CommandLineInterface.java
cli.jar: out/cli/CoomandLineInterface.class src/cli/MANIFEST.MF
	jar cvfm cli.jar src/cli/MANIFEST.MF -C out ./

out/gui/GUI.class: src/gui/GUI.java
	javac -d out/ -sourcepath src/ src/gui/GUI.java
gui.jar: out/gui/GUI.class src/gui/MANIFEST.MF
	jar cvfm gui.jar src/gui/MANIFEST.MF -C out ./
clean:
	rm -rf out/
	rm -f cli.jar
	rm -f gui.jar