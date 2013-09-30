#!/bin/bash

# Clone sk89q-command-framework
git clone https://github.com/OvercastNetwork/sk89q-command-framework
cd sk89q-command-framework
mvn install -DskipTests=true
