import { CardActions, CardContent, List, Typography } from "@mui/material";
import { styled } from "@mui/material/styles";
import { FunctionComponent } from "react";

import { ActionListItem } from "./ActionListItem";
import { SectionText } from "./SectionText";
import { Section } from "../../api";

type SectionContentProps = {
  section: Section | null;
  onTurnTo: Function;
};

const SectionNumber = styled(Typography)`
  font-size: 20px;
  text-align: center;
`;

const Actions = styled(CardActions)`
  justify-content: end;
`;

export const SectionContent: FunctionComponent<SectionContentProps> = ({
  section,
  onTurnTo,
}) => {
  if (!section) {
    return null;
  }
  return (
    <>
      <CardContent>
        <SectionNumber color="textSecondary" gutterBottom>
          {section.reference}
        </SectionNumber>
        <SectionText text={section.text} storyId={section.storyId} />
      </CardContent>
      <Actions>
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
      </Actions>
    </>
  );
};
