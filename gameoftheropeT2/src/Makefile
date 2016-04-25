######################################################################
##																	##
## Existe 3 regras possíveis										##
## make all (ou make) 												##
##		-> Compila todo o código fonte.								##
## make run 														##
##		-> Executa uma vez a simulação.								##
##		NOTA: Convém compilar o código primeiro, se não dá erro.	##
## make clean 														##
##		-> Limpa todos os ficheiros .class 	 						##
##																	##
######################################################################

JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java -d __bin

all:	__bin classes

clean:
	rm -r __bin/

CLASSES = \
		ServerSide/Bench/MBench.java \
		ServerSide/Bench/BenchExec.java \
		ServerSide/Bench/BenchInterface.java \
                ServerSide/Bench/IRefereeBench.java \
                ServerSide/Bench/ICoachBench.java \
                ServerSide/Bench/IContestantsBench.java \

		ServerSide/Playground/MPlayground.java \
		ServerSide/Playground/PlaygroundExec.java \
		ServerSide/Playground/PlaygroundInterface.java \
                ServerSide/Bench/IRefereePlayground.java \
                ServerSide/Bench/ICoachPlayground.java \
                ServerSide/Bench/IContestantsPlayground.java \

		ServerSide/Repository/Shop.java \
		ServerSide/Repository/ShopExec.java \
		ServerSide/Repository/RepositoryInterface.java \
		
                ServerSide/Site/Site.java \
		ServerSide/Site/SiteExec.java \
		ServerSide/Site/SiteInterface.java \

		Communication/ProbConst.java \
		Communication/ClientComm.java \
		Communication/CommConst.java \
		Communication/ServerComm.java \
		Communication/Proxy/ClientProxy.java \
		Communication/Proxy/ServerInterface.java \
		Communication/Message/Message.java \
		Communication/Message/MessageException.java \
		Communication/Message/MessageType.java \

		ClientSide/Referee/Referee.java \
		ClientSide/Referee/RefereeExec.java \
		ClientSide/Referee/ERefereeState.java \

		ClientSide/Coach/Coach.java \
		ClientSide/Coach/CoachExec.java \
		ClientSide/Coach/ECoachState.java \

		ClientSide/Contestant/Contestant.java \
		ClientSide/Contestant/ContestantExec.java \
		ClientSide/Contestant/EContestantsState.java 

classes: $(CLASSES:.java=.class)

__bin:
	mkdir __bin

