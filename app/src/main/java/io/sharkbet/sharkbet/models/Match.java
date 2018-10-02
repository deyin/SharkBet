package io.sharkbet.sharkbet.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.sharkbet.sharkbet.converters.DateConverter;
import io.sharkbet.sharkbet.converters.JsonObjectConverter;

@Entity(tableName = "t_match")
@JsonDeserialize(using = Match.MatchDeserializer.class)
public class Match {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String sportteryMatchId;

    private String okoooMatchId;

    private int sequence;

    @TypeConverters({DateConverter.class})
    private Date time;

    @TypeConverters({JsonObjectConverter.class})
    @Embedded(prefix = "league")
    private League league;

    @TypeConverters({JsonObjectConverter.class})
    @Embedded(prefix = "home")
    private Team home;

    @TypeConverters({JsonObjectConverter.class})
    @Embedded(prefix = "away")
    private Team away;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public Team getHome() {
        return home;
    }

    public void setHome(Team home) {
        this.home = home;
    }

    public Team getAway() {
        return away;
    }

    public void setAway(Team away) {
        this.away = away;
    }

    public String getSportteryMatchId() {
        return sportteryMatchId;
    }

    public void setSportteryMatchId(String sportteryMatchId) {
        this.sportteryMatchId = sportteryMatchId;
    }

    public String getOkoooMatchId() {
        return okoooMatchId;
    }

    public void setOkoooMatchId(String okoooMatchId) {
        this.okoooMatchId = okoooMatchId;
    }

    public static class League {
        private String id;

        private String name;
        private String abbrName;

        private int round;

        public League(String id, String name, String abbrName, int round) {
            this.id = id;
            this.name = name;
            this.abbrName = abbrName;
            this.round = round;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAbbrName() {
            return abbrName;
        }

        public void setAbbrName(String abbrName) {
            this.abbrName = abbrName;
        }

        public int getRound() {
            return round;
        }

        public void setRound(int round) {
            this.round = round;
        }
    }

    public static class Team {
        private String id;
        private String name;
        private String abbrName;
        private int order;
        @TypeConverters({JsonObjectConverter.class})
        @Embedded
        private Standings standings;

        public Team(String id, String name, String abbrName, int order, Standings standings) {
            this.id = id;
            this.name = name;
            this.abbrName = abbrName;
            this.order = order;
            this.standings = standings;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAbbrName() {
            return abbrName;
        }

        public void setAbbrName(String abbrName) {
            this.abbrName = abbrName;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public Standings getStandings() {
            return standings;
        }

        public void setStandings(Standings standings) {
            this.standings = standings;
        }

        public static class Standings {
            private int win;
            private int draw;
            private int lose;

            private int points;

            public Standings(int win, int draw, int lose, int points) {
                this.win = win;
                this.draw = draw;
                this.lose = lose;
                this.points = points;
            }

            public int getWin() {
                return win;
            }

            public void setWin(int win) {
                this.win = win;
            }

            public int getDraw() {
                return draw;
            }

            public void setDraw(int draw) {
                this.draw = draw;
            }

            public int getLose() {
                return lose;
            }

            public void setLose(int lose) {
                this.lose = lose;
            }

            public int getPoints() {
                return points;
            }

            public void setPoints(int points) {
                this.points = points;
            }

            @Override
            public String toString() {
                return "Standings{" +
                        "win=" + win +
                        ", draw=" + draw +
                        ", lose=" + lose +
                        ", points=" + points +
                        '}';
            }
        } // end class Standings

        @Override
        public String toString() {
            return "Team{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", abbrName='" + abbrName + '\'' +
                    ", order=" + order +
                    ", standings=" + standings +
                    '}';
        }
    } // end class Team

    @Override
    public String toString() {
        return "Match{" +
                "id='" + id + '\'' +
                ", sequence=" + sequence +
                ", time=" + time +
                ", league=" + league +
                ", home=" + home +
                ", away=" + away +
                '}';
    }

    static class MatchDeserializer extends StdDeserializer<Match> {

        static Pattern PATTERN_NUM = Pattern.compile("周([一二三四五六日])(.*)");

        public MatchDeserializer() {
            this(null);
        }

        protected MatchDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Match deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            Match match = new Match();

            JsonNode root = parser.getCodec().readTree(parser);

            // sportteryId
            match.setSportteryMatchId(root.get("sporttery_matchid").asText());

            // sequence
            String text = root.get("s_num").asText();
            Matcher matcher = PATTERN_NUM.matcher(text);
            if (matcher.find()) {
                String number = matcher.group(2);
                match.setSequence(Integer.parseInt(number));
            }

            // time
            Date date = DateConverter.dateFromYmdHmsString(root.get("date_cn").asText() + " " + root.get("time_cn").asText());
            match.setTime(date);

            // league
            League league = new League(root.get("l_id_dc").asText(), root.get("l_cn").asText(),
                    root.get("l_cn_abbr").asText(), Integer.parseInt(root.get("gameweek").asText()));
            match.setLeague(league);

            // home team
            Team.Standings homeStandings  = new Team.Standings(
                      Integer.parseInt(root.get("hwin_h").asText())
                    , Integer.parseInt(root.get("hdraw_h").asText())
                    , Integer.parseInt(root.get("hlose_h").asText())
                    , Integer.parseInt(root.get("hpoint_h").asText()));
            Team home = new Team(
                    root.get("h_id_dc").asText(), root.get("h_cn").asText(),
                    root.get("h_cn_abbr").asText(),
                    Integer.parseInt(root.get("table_h").asText()), homeStandings);
            match.setHome(home);

            // away team
            Team.Standings awayStandings  = new Team.Standings(
                    Integer.parseInt(root.get("awin_a").asText())
                    , Integer.parseInt(root.get("adraw_a").asText())
                    , Integer.parseInt(root.get("alose_a").asText())
                    , Integer.parseInt(root.get("apoint_a").asText()));
            Team away = new Team(
                    root.get("a_id_dc").asText(), root.get("a_cn").asText(),
                    root.get("a_cn_abbr").asText(),
                    Integer.parseInt(root.get("table_a").asText()), awayStandings);
            match.setAway(away);

            return match;
        }
    }
} // end class Match
