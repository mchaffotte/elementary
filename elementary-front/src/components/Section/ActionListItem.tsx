import {
  IconButton,
  ListItem,
  ListItemSecondaryAction,
  ListItemText,
  TextField,
} from "@material-ui/core";
import NavigateNextIcon from "@material-ui/icons/NavigateNext";
import { FunctionComponent, useState } from "react";

import { Action } from "../../api";

type ActionListItemProps = {
  index: number;
  action: Action;
  onTurnTo: Function;
};

export const ActionListItem: FunctionComponent<ActionListItemProps> = ({
  index,
  action,
  onTurnTo,
}) => {
  const [answer, setAnswer] = useState("");

  const handleAnswerChange = (event: any) => {
    setAnswer(event.target.value);
  };

  return (
    <ListItem>
      <ListItemText
        primary={
          action.answerNeeded ? (
            <TextField
              label="Enter your answer"
              onChange={handleAnswerChange}
            />
          ) : (
            action.description
          )
        }
      />
      <ListItemSecondaryAction>
        <IconButton
          edge="end"
          aria-label="next"
          onClick={() => {
            onTurnTo(index, answer);
          }}
        >
          <NavigateNextIcon />
        </IconButton>
      </ListItemSecondaryAction>
    </ListItem>
  );
};
