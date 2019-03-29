import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IItem } from 'app/shared/model/item.model';
import { getEntities as getItems } from 'app/entities/item/item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './item-cost.reducer';
import { IItemCost } from 'app/shared/model/item-cost.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IItemCostUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IItemCostUpdateState {
  isNew: boolean;
  itemId: string;
}

export class ItemCostUpdate extends React.Component<IItemCostUpdateProps, IItemCostUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      itemId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getItems();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { itemCostEntity } = this.props;
      const entity = {
        ...itemCostEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/item-cost');
  };

  render() {
    const { itemCostEntity, items, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="lenderApp.itemCost.home.createOrEditLabel">
              <Translate contentKey="lenderApp.itemCost.home.createOrEditLabel">Create or edit a ItemCost</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : itemCostEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="item-cost-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="timeFrameLabel">
                    <Translate contentKey="lenderApp.itemCost.timeFrame">Time Frame</Translate>
                  </Label>
                  <AvInput
                    id="item-cost-timeFrame"
                    type="select"
                    className="form-control"
                    name="timeFrame"
                    value={(!isNew && itemCostEntity.timeFrame) || 'MINUTE'}
                  >
                    <option value="MINUTE">
                      <Translate contentKey="lenderApp.TimeFrame.MINUTE" />
                    </option>
                    <option value="HOUR">
                      <Translate contentKey="lenderApp.TimeFrame.HOUR" />
                    </option>
                    <option value="DAY">
                      <Translate contentKey="lenderApp.TimeFrame.DAY" />
                    </option>
                    <option value="MONTH">
                      <Translate contentKey="lenderApp.TimeFrame.MONTH" />
                    </option>
                    <option value="YEAR">
                      <Translate contentKey="lenderApp.TimeFrame.YEAR" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="costPerTimeFrameLabel" for="costPerTimeFrame">
                    <Translate contentKey="lenderApp.itemCost.costPerTimeFrame">Cost Per Time Frame</Translate>
                  </Label>
                  <AvField
                    id="item-cost-costPerTimeFrame"
                    type="text"
                    name="costPerTimeFrame"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="item.name">
                    <Translate contentKey="lenderApp.itemCost.item">Item</Translate>
                  </Label>
                  <AvInput id="item-cost-item" type="select" className="form-control" name="itemId">
                    {items
                      ? items.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/item-cost" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  items: storeState.item.entities,
  itemCostEntity: storeState.itemCost.entity,
  loading: storeState.itemCost.loading,
  updating: storeState.itemCost.updating,
  updateSuccess: storeState.itemCost.updateSuccess
});

const mapDispatchToProps = {
  getItems,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ItemCostUpdate);
