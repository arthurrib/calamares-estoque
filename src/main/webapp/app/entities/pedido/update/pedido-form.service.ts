import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPedido, NewPedido } from '../pedido.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPedido for edit and NewPedidoFormGroupInput for create.
 */
type PedidoFormGroupInput = IPedido | PartialWithRequiredKeyOf<NewPedido>;

type PedidoFormDefaults = Pick<NewPedido, 'id'>;

type PedidoFormGroupContent = {
  id: FormControl<IPedido['id'] | NewPedido['id']>;
  dataPedido: FormControl<IPedido['dataPedido']>;
  status: FormControl<IPedido['status']>;
  valorTotal: FormControl<IPedido['valorTotal']>;
};

export type PedidoFormGroup = FormGroup<PedidoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PedidoFormService {
  createPedidoFormGroup(pedido: PedidoFormGroupInput = { id: null }): PedidoFormGroup {
    const pedidoRawValue = {
      ...this.getFormDefaults(),
      ...pedido,
    };
    return new FormGroup<PedidoFormGroupContent>({
      id: new FormControl(
        { value: pedidoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      dataPedido: new FormControl(pedidoRawValue.dataPedido, {
        validators: [Validators.required],
      }),
      status: new FormControl(pedidoRawValue.status),
      valorTotal: new FormControl(pedidoRawValue.valorTotal),
    });
  }

  getPedido(form: PedidoFormGroup): IPedido | NewPedido {
    return form.getRawValue() as IPedido | NewPedido;
  }

  resetForm(form: PedidoFormGroup, pedido: PedidoFormGroupInput): void {
    const pedidoRawValue = { ...this.getFormDefaults(), ...pedido };
    form.reset(
      {
        ...pedidoRawValue,
        id: { value: pedidoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PedidoFormDefaults {
    return {
      id: null,
    };
  }
}
