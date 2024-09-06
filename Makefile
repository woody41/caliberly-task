package:
	mvn clean compile assembly:single
build:
	mvn clean install
run:
	java -jar ./target/casino-game-1.0-jar-with-dependencies.jar --config config.json --betting-amount 500
