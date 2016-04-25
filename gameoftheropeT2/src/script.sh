set -x
echo "	> Compiling all source code to a directory __bin (using a Makefile)"
make;

echo "	> Creating directory jars if it doesn't exist"
if [[ ! -e Jars ]]; then
    mkdir Jars;
fi

echo "	> Generating all different jars (7 at total)"
(cd __bin;
jar cfe Bench.jar ServerSide.Bench.BenchExec ./
jar cfe Playground.jar ServerSide.Playground.PlaygroundExec ./
jar cfe Site.jar ServerSide.Site.SiteExec ./
jar cfe Repository.jar ServerSide.Repository.RepositoryExec ./
jar cfe Coach.jar ClientSide.Coach.CoachExec ./
jar cfe Contestant.jar ClientSide.Contestant.ContestantExec ./
jar cfe Referee.jar ClientSide.Referee.RefereeExec ./
mv *.jar ../Jars/)

echo "	> Sending the proper jar to the correct workstation"
(cd Jars;
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Repository.jar sd0405@l040101-ws01.ua.pt:~
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Playground.jar sd0405@l040101-ws05.ua.pt:~
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Site.jar sd0405@l040101-ws04.ua.pt:~
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Bench.jar sd0405@l040101-ws03.ua.pt:~
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Referee.jar sd0405@l040101-ws02.ua.pt:~
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Contestant.jar sd0405@l040101-ws08.ua.pt:~
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Coach.jar sd0105@l040101-ws09.ua.pt:~
)

echo "  > Cleaning logs from the server where Logging is going to run"
sshpass -p sistema2015 ssh sd0405@l040101-ws01.ua.pt -o StrictHostKeyChecking 'rm *.log'

echo "	> Executing each jar file on the proper workstation"
sshpass -p sistema2015 ssh sd0405@l040101-ws01.ua.pt -o StrictHostKeyChecking=no 'java -jar Repository.jar' &
PID_Logging=$!
sshpass -p qualquercoisa ssh sd0405@l040101-ws05.ua.pt -o StrictHostKeyChecking=no 'java -jar Playground.jar' &
sshpass -p qualquercoisa ssh sd0405@l040101-ws04.ua.pt -o StrictHostKeyChecking=no 'java -jar Site.jar' &
sshpass -p qualquercoisa ssh sd0405@l040101-ws03.ua.pt -o StrictHostKeyChecking=no 'java -jar Bench.jar' &
sshpass -p qualquercoisa ssh sd0405@l040101-ws02.ua.pt -o StrictHostKeyChecking=no 'java -jar Referee.jar' &
sshpass -p qualquercoisa ssh sd0405@l040101-ws08.ua.pt -o StrictHostKeyChecking=no 'java -jar Contestant.jar' &
sshpass -p qualquercoisa ssh sd0405@l040101-ws09.ua.pt -o StrictHostKeyChecking=no 'java -jar Coach.jar' &
echo "	> Waiting for simulation to end (generate a logging file).."
wait $PID_Logging
echo "  > Simulation ended, copying log file to the local machine"
if [[ ! -e FinalLogs ]]; then
    mkdir FinalLogs;
fi
sshpass -p sistema2015 scp -o StrictHostKeyChecking=no sd0405@l040101-ws01.ua.pt:~/Artesanato* FinalLogs/