
JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java -d __bin

all:	__bin classes

clean:
	rm -r __bin/

CLASSES = \
        Interfaces/*.java \
        Registry/RegisterRemoteObject.java \
        Registry/ServerRegisterRemoteObject.java \
        Structures/Constants/*.java \
        Structures/Enumerates/*.java \
        Structures/VectorClock/*.java \
        ServerSide/Bench/MBench.java \
        ServerSide/Bench/BenchExec.java \
        ServerSide/Playground/MPlayground.java \
        ServerSide/Playground/PlaygroundExec.java \
        ServerSide/Site/MSite.java \
        ServerSide/Site/SiteExec.java \
        ServerSide/Repository/MRepository.java \
        ServerSide/Repository/RepositoryExec.java \
        ClientSide/Coach/Coach.java \
        ClientSide/Coach/CoachExec.java \
        ClientSide/Contestant/Contestant.java \
        ClientSide/Contestant/ContestantExec.java \
        ClientSide/Referee/Referee.java \
        ClientSide/Referee/RefereeExec.java \


classes: $(CLASSES:.java=.class)

__bin:  
	mkdir __bin

