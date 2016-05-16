
JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java -d __bin

all:	__bin classes

clean:
	rm -r __bin/

CLASSES = \
        Communication/ConstConfigs.java \
        ClientSide/Coach/ECoachesState.java \
        ClientSide/Contestant/EContestantsState.java \
        ClientSide/Referee/ERefereeState.java \
        ClientSide/Referee/Referee.java \
        ClientSide/Referee/RefereeExec.java \
        ClientSide/Coach/Coach.java \
        ClientSide/Coach/CoachExec.java \
        ClientSide/Contestant/Contestant.java \
        ClientSide/Contestant/ContestantExec.java \
        ServerSide/Bench/MBench.java \
        ServerSide/Bench/BenchInterface.java \
        ServerSide/Bench/BenchExec.java \
        ServerSide/Playground/PlaygroundInterface.java \
        ServerSide/Playground/MPlayground.java \
        ServerSide/Playground/PlaygroundExec.java \
        ServerSide/Site/MSite.java \
        ServerSide/Site/SiteInterface.java \
        ServerSide/Site/SiteExec.java \
        ServerSide/Repository/MRepository.java \
        ServerSide/Repository/RepositoryExec.java \

classes: $(CLASSES:.java=.class)

__bin:
	mkdir __bin

