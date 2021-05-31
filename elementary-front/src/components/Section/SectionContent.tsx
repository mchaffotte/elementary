import {
  CardActions,
  CardContent,
  List,
  Typography,
  makeStyles,
} from "@material-ui/core";
import { FunctionComponent } from "react";

import { ActionListItem } from "./ActionListItem";
import { SectionText } from "./SectionText";
import { Section } from "../../api";

const useStyles = makeStyles({
  title: {
    fontSize: 20,
    textAlign: "center",
  },
  actions: {
    justifyContent: "end",
  },
});

type SectionContentProps = {
  section: Section | null;
  onTurnTo: Function;
};

export const SectionContent: FunctionComponent<SectionContentProps> = ({
  section,
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
        <SectionText text={section.text} storyId={section.storyId} />
      </CardContent>
      <CardActions className={classes.actions}>
        <List>
          {section.actions.map((action, index) => (
            <ActionListItem
              key={index}
              index={index}
              action={action}
              onTurnTo={onTurnTo}
            />
          ))}
        </List>
      </CardActions>
    </>
  );
};
