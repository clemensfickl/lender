import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IItem } from 'app/shared/model/item.model';
import { getEntities as getItems } from 'app/entities/item/item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './lending.reducer';
import { ILending } from 'app/shared/model/lending.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ILendingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ILendingUpdateState {
  isNew: boolean;
  borrowerId: string;
  itemId: string;
}

export class LendingUpdate extends React.Component<ILendingUpdateProps, ILendingUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      borrowerId: '0',
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

    this.props.getUsers();
    this.props.getItems();
  }

  saveEntity = (event, errors, values) => {
    values.start = convertDateTimeToServer(values.start);
    values.plannedEnd = convertDateTimeToServer(values.plannedEnd);
    values.end = convertDateTimeToServer(values.end);

    if (errors.length === 0) {
      const { lendingEntity } = this.props;
      const entity = {
        ...lendingEntity,
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
    this.props.history.push('/entity/lending');
  };

  render() {
    const { lendingEntity, users, items, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="lenderApp.lending.home.createOrEditLabel">
              <Translate contentKey="lenderApp.lending.home.createOrEditLabel">Create or edit a Lending</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : lendingEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="lending-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="startLabel" for="start">
                    <Translate contentKey="lenderApp.lending.start">Start</Translate>
                  </Label>
                  <AvInput
                    id="lending-start"
                    type="datetime-local"
                    className="form-control"
                    name="start"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.lendingEntity.start)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="plannedEndLabel" for="plannedEnd">
                    <Translate contentKey="lenderApp.lending.plannedEnd">Planned End</Translate>
                  </Label>
                  <AvInput
                    id="lending-plannedEnd"
                    type="datetime-local"
                    className="form-control"
                    name="plannedEnd"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.lendingEntity.plannedEnd)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="endLabel" for="end">
                    <Translate contentKey="lenderApp.lending.end">End</Translate>
                  </Label>
                  <AvInput
                    id="lending-end"
                    type="datetime-local"
                    className="form-control"
                    name="end"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.lendingEntity.end)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="informedAboutEndLabel" check>
                    <AvInput id="lending-informedAboutEnd" type="checkbox" className="form-control" name="informedAboutEnd" />
                    <Translate contentKey="lenderApp.lending.informedAboutEnd">Informed About End</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="costLabel" for="cost">
                    <Translate contentKey="lenderApp.lending.cost">Cost</Translate>
                  </Label>
                  <AvField id="lending-cost" type="text" name="cost" />
                </AvGroup>
                <AvGroup>
                  <Label id="paidLabel" check>
                    <AvInput id="lending-paid" type="checkbox" className="form-control" name="paid" />
                    <Translate contentKey="lenderApp.lending.paid">Paid</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="borrower.login">
                    <Translate contentKey="lenderApp.lending.borrower">Borrower</Translate>
                  </Label>
                  <AvInput id="lending-borrower" type="select" className="form-control" name="borrowerId">
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.login}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="item.name">
                    <Translate contentKey="lenderApp.lending.item">Item</Translate>
                  </Label>
                  <AvInput id="lending-item" type="select" className="form-control" name="itemId">
                    {items
                      ? items.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/lending" replace color="info">
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
  users: storeState.userManagement.users,
  items: storeState.item.entities,
  lendingEntity: storeState.lending.entity,
  loading: storeState.lending.loading,
  updating: storeState.lending.updating,
  updateSuccess: storeState.lending.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
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
)(LendingUpdate);
