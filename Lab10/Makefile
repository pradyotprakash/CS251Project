.PHONY: gale galerun merit meritrun all run common doc clean
all: gale merit doc
	@echo "Compiling Main.java..."
	@javac code/Main/Main.java

doc:
	@mkdir -p doc
	@echo "Generating documentation..."
	@javadoc -private -d doc code/common/*.java code/merit/*.java \
				code/gale/*.java code/Main/*.java > /dev/null
	@pdflatex doc/report.tex > /dev/null 2> /dev/null && mv report.* doc
	@pdflatex doc/executive.tex > /dev/null 2> /dev/null && mv executive.* doc
	@mv doc/*.pdf .
	@rm -f doc/*.aux doc/*.log doc/*.snm doc/*.nav doc/*.toc doc/*.out	

common:
	@echo "Compiling Constants.java..."
	@javac code/common/Constants.java
	@echo "Compiling Candidate.java..."
	@javac code/common/Candidate.java
	@echo "Compiling MeritList.java..."
	@javac code/common/MeritList.java
	@echo "Compiling VirtualProgramme.java..."
	@javac code/common/VirtualProgramme.java

gale: common
	@echo "Compiling GaleShapelyAdmission.java..."
	@javac code/gale/GaleShapelyAdmission.java

merit: common
	@echo "Compiling MeritOrderAdmission.java..."
	@javac code/merit/MeritOrderAdmission.java

clean:
	@echo "Cleaning up..."
	@cd code/common && rm -f *.class
	@cd code/merit && rm -f *.class
	@cd code/gale && rm -f *.class
	@cd code/Main && rm -f *.class
	@mv doc/*.tex .
	@rm -fr doc/*
	@mv *.tex doc
	@rm -f *.pdf
