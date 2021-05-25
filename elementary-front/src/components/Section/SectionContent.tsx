import {
  CardActions,
  CardContent,
  IconButton,
  List,
  ListItem,
  ListItemSecondaryAction,
  ListItemText,
  Typography,
  makeStyles,
} from "@material-ui/core";
import NavigateNextIcon from "@material-ui/icons/NavigateNext";
import { FunctionComponent } from "react";

import { SectionText } from "./SectionText";
import { Section } from "../../api";

const useStyles = makeStyles({
  title: {
    fontSize: 20,
    textAlign: "center",
  },
});

type SectionContentProps = {
  section: Section | null;
  storyId: number;
  onTurnTo: Function;
};

export const SectionContent: FunctionComponent<SectionContentProps> = ({
  section,
  storyId,
  onTurnTo,
}) => {
  const classes = useStyles();

  if (!section) {
    return null;
  }
  return (
    <>
      <CardContent>
        <Typography
          className={classes.title}
          color="textSecondary"
          gutterBottom
        >
          {section.reference}
        </Typography>
        <SectionText text={section.text} storyId={storyId} />
      </CardContent>
      <CardActions>
        <List>
          {section.actions.map((action, index) => (
            <ListItem key={index}>
              <ListItemText primary={action.description} />
              <ListItemSecondaryAction>
                <IconButton
                  edge="end"
                  aria-label="next"
                  onClick={() => {
                    onTurnTo(index);
                  }}
                >
                  <NavigateNextIcon />
                </IconButton>
              </ListItemSecondaryAction>
            </ListItem>
          ))}
        </List>
      </CardActions>
    </>
  );
};
