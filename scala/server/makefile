# Constants

COLOR_BLUE=\033[0;34m
COLOR_NONE=\033[0m

# Targets

help:
	@grep -E '^[0-9a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) \
		| sort \
		| awk 'BEGIN {FS = ":.*?## "}; {printf "$(COLOR_BLUE)%s|$(COLOR_NONE)%s\n", $$1, $$2}' \
		| column -t -s '|'

.PHONY: clean
clean: ## Remove all built artefacts
	@echo 'Removing artefacts'
	@sbt clean

.PHONY: test
test: ## Run tests
	@echo 'Running tests'
	@sbt test

dist: ## Build the distribution
	@echo 'Building the distribution'
	@sbt assembly

run: dist ## Build the distribution and run it
	@echo 'Running application'
	@java -jar ./target/scala-2.13/starter-assembly-0.1.0-SNAPSHOT.jar
