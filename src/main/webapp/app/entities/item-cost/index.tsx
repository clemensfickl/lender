import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ItemCost from './item-cost';
import ItemCostDetail from './item-cost-detail';
import ItemCostUpdate from './item-cost-update';
import ItemCostDeleteDialog from './item-cost-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ItemCostUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ItemCostUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ItemCostDetail} />
      <ErrorBoundaryRoute path={match.url} component={ItemCost} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ItemCostDeleteDialog} />
  </>
);

export default Routes;
