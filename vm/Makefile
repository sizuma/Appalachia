
JAVAC = javac
SRC = ./src
SRC_JAVA = $(SRC)/cli/CommandLineInterface.java

JAVA_OUT = ./out
JAVAC_FLAGS = -d $(JAVA_OUT) -sourcepath $(SRC)

JAR = jar
JAR_FILE = vm.jar
MANIFEST = MANIFEST.MF
JAR_FLAGS = cvfm $(JAR_FILE) $(MANIFEST) -C $(JAVA_OUT)

all: jar

compile:
	$(JAVAC) $(SRC_JAVA) $(JAVAC_FLAGS)

jar: compile
	$(JAR) $(JAR_FLAGS) .

clean-compile:
	rm -rf out/
clean-jar:
	rm $(JAR_FILE)
clean: clean-compile clean-jar