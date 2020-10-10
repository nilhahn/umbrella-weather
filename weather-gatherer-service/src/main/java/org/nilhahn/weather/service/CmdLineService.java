package org.nilhahn.weather.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

import java.util.Optional;

@Slf4j
public class CmdLineService {
    private Options options = new Options();
    private CommandLine cmd = null;

    public enum Parameter {
        APIKEY("apiKey"),
        LOCATION("location");

        private String parameter;

        Parameter(final String parameter) {
            this.parameter = parameter;
        }

        public String asString() {
            return this.parameter;
        }
    }

    public CmdLineService() {
        Option apiKey = new Option("key", "apiKey", true, "open weather map api key");
        Option location = new Option("l", "location", true, "open weather location");
        apiKey.setRequired(true);
        location.setRequired(false);
        this.options.addOption(apiKey);
        this.options.addOption(location);
    }

    public void parse(String[] args) {
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            cmd = parser.parse(this.options, args);
        } catch (ParseException e) {
            log.error(e.getMessage());
            formatter.printHelp("weather-gatherer-service", this.options);

            System.exit(0);
        }
    }

    public Optional<String> getCmdLineParameter(Parameter parameter) {
            return Optional.ofNullable(this.cmd.getOptionValue(parameter.asString()));
    }
}
