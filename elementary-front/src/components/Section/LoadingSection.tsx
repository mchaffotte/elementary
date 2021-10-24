import { CardContent, Skeleton } from "@mui/material";
import { styled } from "@mui/material/styles";
import { FunctionComponent } from "react";

const SectionNumber = styled(Skeleton)`
  margin: 0 auto 15px;
`;

const ParagraphEnd = styled(Skeleton)`
  margin-bottom: 15px;
`;

export const LoadingSection: FunctionComponent<{}> = () => {
  return (
    <CardContent>
      <SectionNumber variant="circular" width={30} height={30} />
      <Skeleton />
      <Skeleton />
      <Skeleton />
      <ParagraphEnd width="60%" />

      <Skeleton />
      <Skeleton />
      <Skeleton />
      <Skeleton width="40%" />
    </CardContent>
  );
};
