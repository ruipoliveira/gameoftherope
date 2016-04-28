set -x
echo "  > Compilar todo o codigo (Makefile)"
make;

echo "  > Criar directorio para guardar ficheiros JAR, senao estiver criado"
if [[ ! -e JARS ]]; then
    mkdir JARS;
fi

echo "  > Gerar JARs"
(cd __bin;
jar cfe Bench.jar ServerSide.Bench.BenchExec ./
jar cfe Playground.jar ServerSide.Playground.PlaygroundExec ./
jar cfe Site.jar ServerSide.Site.SiteExec ./
jar cfe Repository.jar ServerSide.Repository.RepositoryExec ./
jar cfe Coach.jar ClientSide.Coach.CoachExec ./
jar cfe Contestant.jar ClientSide.Contestant.ContestantExec ./
jar cfe Referee.jar ClientSide.Referee.RefereeExec ./
mv *.jar ../JARS/)

echo "  > Enviar JARs para as diferentes maquinas"
(cd JARS;
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Repository.jar sd0105@l040101-ws01.ua.pt:~
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Playground.jar sd0105@l040101-ws05.ua.pt:~
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Site.jar sd0105@l040101-ws04.ua.pt:~
sshpass -p qwerty scp -o StrictHostKeyChecking=no Bench.jar sd0105@l040101-ws03.ua.pt:~
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Referee.jar sd0105@l040101-ws02.ua.pt:~
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Contestant.jar sd0105@l040101-ws06.ua.pt:~
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Coach.jar sd0105@l040101-ws08.ua.pt:~
)


