set -x
echo "  > Compilar todo o codigo (Makefile)"
make;

echo "  > Criar directorio para guardar ficheiros JAR, senao estiver criado"
if [[ ! -e JARS ]]; then
    mkdir JARS;
fi

echo "  > Gerar JARs"
(cd __bin;
jar cfe RMI_Server.jar Registry.ServerRegisterRemoteObject ./
jar cfe Bench_vRMI.jar ServerSide.Bench.BenchExec ./
jar cfe Playground_vRMI.jar ServerSide.Playground.PlaygroundExec ./
jar cfe Site_vRMI.jar ServerSide.Site.SiteExec ./
jar cfe Repository_vRMI.jar ServerSide.Repository.RepositoryExec ./
jar cfe Coach_vRMI.jar ClientSide.Coach.CoachExec ./
jar cfe Contestant_vRMI.jar ClientSide.Contestant.ContestantExec ./
jar cfe Referee_vRMI.jar ClientSide.Referee.RefereeExec ./
mv *.jar ../JARS/)

echo "  > Enviar JARs para as diferentes maquinas"
(cd JARS;
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no RMI_Server.jar sd0105@l040101-ws07.ua.pt:~
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Repository_vRMI.jar sd0105@l040101-ws01.ua.pt:~
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Playground_vRMI.jar sd0105@l040101-ws05.ua.pt:~
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Site_vRMI.jar sd0105@l040101-ws04.ua.pt:~
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Bench_vRMI.jar sd0105@l040101-ws03.ua.pt:~
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Referee_vRMI.jar sd0105@l040101-ws02.ua.pt:~
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Contestant_vRMI.jar sd0105@l040101-ws06.ua.pt:~
sshpass -p qualquercoisa scp -o StrictHostKeyChecking=no Coach_vRMI.jar sd0105@l040101-ws08.ua.pt:~
)


