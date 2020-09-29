package Match;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ArgumentParser {

    private Integer noOfOutputTeams;
    private String preMatchPlayersDataInputFile;
    private String outputFile;
    private String postMatchPlayersDataInputFile;

    public Integer getNoOfTeams() {
        return noOfOutputTeams;
    }

    public String getInputFile() {
        return preMatchPlayersDataInputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public String getInputDreamTeam() {
        return postMatchPlayersDataInputFile;
    }

    public Options options() {
        Options options = new Options();
        options.addOption(Option.builder("t").longOpt("noOfTeams").desc("no. of teams in the output").hasArg(true)
                .type(Integer.class).required(true).build());
        options.addOption(
                Option.builder("i").longOpt("inputFile").hasArg(true).desc("Input File").required(true).build());
        options.addOption(Option.builder("o").longOpt("outputFile").hasArg(true).desc("output excel file")
                .required(true).build());
        options.addOption(Option.builder("idt").desc("input DreamTeam file to check accuracy").hasArg(true)
                .required(false).build());
        return options;
    }

    public void parseArguments(Options options, String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        noOfOutputTeams = Integer.parseInt(cmd.getOptionValue("t"));
        preMatchPlayersDataInputFile = cmd.getOptionValue("i");
        outputFile = cmd.getOptionValue("o");
        if (cmd.hasOption("idt")) {
            postMatchPlayersDataInputFile = cmd.getOptionValue("idt");
        }

    }

}
